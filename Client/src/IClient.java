import java.io.IOException;

public interface IClient {
    void authorization() throws IOException;

    void messaging() throws IOException;
}