package cz.fi.muni.pa165.yellow_yak.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Matej Horniak
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name="player",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"})
)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    private LocalDateTime createdAt;

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Player)) return false;
        Player player = (Player) obj;
        return Objects.equals(getUsername(), player.getUsername());
    }
}