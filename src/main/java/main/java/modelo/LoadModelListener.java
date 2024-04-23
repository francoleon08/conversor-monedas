package main.java.modelo;

public interface LoadModelListener {

    void loadModelFinished();

    void loadModelFailed(String msg);
}
