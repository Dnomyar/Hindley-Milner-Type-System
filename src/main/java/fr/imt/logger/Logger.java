package fr.imt.logger;

public interface Logger {
    void debug(String msg);
    void trace(String msg);
    void reset();
}
