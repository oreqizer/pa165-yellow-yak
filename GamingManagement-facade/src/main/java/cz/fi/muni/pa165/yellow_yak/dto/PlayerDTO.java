package cz.fi.muni.pa165.yellow_yak.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Matej Horniak
 */
@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO {
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
        if (!(obj instanceof PlayerDTO)) return false;
        PlayerDTO player = (PlayerDTO) obj;
        return Objects.equals(getUsername(), player.getUsername());
    }
}