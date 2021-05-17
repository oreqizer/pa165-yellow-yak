import React from "react";
import * as Yup from "yup";
import { Redirect, Route, Link, Switch } from "wouter";
import { Alert, Button, Col, Container, Form, InputGroup, Nav, Navbar, Row } from "react-bootstrap";
import { Formik } from "formik";

import Player from "./Players";
import { save } from "../services/storage";
import { login } from "../services/auth";
import useState, { isSuccess } from "../services/useState";

enum Routes {
  INDEX = "/",
  PLAYERS = "/players",
}

type FormValues = {
  email: string;
  password: string;
};

const Index = () => {
  const [state, setState] = useState();
  const success = isSuccess(state);

  const validationSchema = React.useMemo(
    () =>
      Yup.object().shape({
        email: Yup.string().email("Invalid email").required("Required"),
        password: Yup.string().required("Required"),
      }),
    [],
  );

  const handleSubmit = React.useCallback((values: FormValues) => {
    login(values)
      .then(({ token }) => {
        save(token);
        setState({ error: null });
      })
      .catch((error) => {
        setState({ error });
      });
  }, []);

  if (!success) {
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md="auto">
            <h1 className="my-5">Gaming management</h1>

            <Formik<FormValues>
              initialValues={{
                email: "",
                password: "",
              }}
              validationSchema={validationSchema}
              onSubmit={handleSubmit}
            >
              {(form) => (
                <Form noValidate onSubmit={form.handleSubmit}>
                  <Form.Group controlId="email">
                    <Form.Label>Email address</Form.Label>
                    <InputGroup hasValidation>
                      <Form.Control
                        name="email"
                        type="email"
                        value={form.values.email}
                        isInvalid={form.touched.email === true && form.errors.email != null}
                        onChange={form.handleChange}
                        onBlur={form.handleBlur}
                        placeholder="Enter email"
                      />
                      <Form.Control.Feedback type="invalid">
                        {form.errors.email}
                      </Form.Control.Feedback>
                    </InputGroup>
                  </Form.Group>

                  <Form.Group controlId="password">
                    <Form.Label>Password</Form.Label>
                    <InputGroup hasValidation>
                      <Form.Control
                        name="password"
                        type="password"
                        value={form.values.password}
                        isInvalid={form.touched.password === true && form.errors.password != null}
                        onChange={form.handleChange}
                        onBlur={form.handleBlur}
                        placeholder="Password"
                      />
                      <Form.Control.Feedback type="invalid">
                        {form.errors.password}
                      </Form.Control.Feedback>
                    </InputGroup>
                  </Form.Group>

                  {state?.error != null && <Alert variant="danger">{state.error.message}</Alert>}

                  <Button
                    variant="primary"
                    type="submit"
                    disabled={!form.isValid || form.isSubmitting}
                  >
                    Sign in
                  </Button>
                </Form>
              )}
            </Formik>
          </Col>
        </Row>
      </Container>
    );
  }

  return (
    <>
      <Navbar variant="light" bg="light" expand="lg">
        <Link href={Routes.INDEX}>
          <Navbar.Brand>Gaming management</Navbar.Brand>
        </Link>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">
            <Link href={Routes.PLAYERS}>
              <Nav.Link>Player</Nav.Link>
            </Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      <Container className="my-3">
        <Row className="justify-content-md-center">
          <Switch>
            <Route path={Routes.PLAYERS}>{() => <Player />}</Route>

            <Redirect to={Routes.INDEX} />
          </Switch>
        </Row>
      </Container>
    </>
  );
};

export default Index;
