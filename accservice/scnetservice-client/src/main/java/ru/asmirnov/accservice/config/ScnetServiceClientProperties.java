package ru.asmirnov.accservice.config;

/**
 * @author Alexey Smirnov at 29/03/2018
 */
public class ScnetServiceClientProperties {

    private String url = "";
    private int port = 0;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ScnetServiceClientProperties{" +
                "url='" + url + '\'' +
                ", port=" + port +
                '}';
    }
}
