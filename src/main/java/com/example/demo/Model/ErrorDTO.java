package com.example.demo.Model;

public class ErrorDTO { // Usare esta clase para mandar errores (GET)
    private String status, error;

    public ErrorDTO(String status, String error) {
        this.status = status; this.error = error;
    }
    public ErrorDTO(){} // A veces queda mas limpio si solo uso setters luego en el controlador

    // Get Set
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }


    @Override
    public String toString() {
        return "Error: " + error + "\n" + "Status: " + status ;
    }
}

