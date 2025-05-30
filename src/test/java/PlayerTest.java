import com.gladkiei.tennisscoreboard.models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    public void newPlayerShouldHaveNoId() {
        Player player = new Player();
        Assertions.assertNull(player.getId());
    }
}
