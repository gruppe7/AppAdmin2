package com.example.andreasbergman.appadmin2;

/**
 * Created by andreasbergman on 20/10/16.
 */

public class Participants {

    private String username;
    private boolean dinnerParticipation;
    private boolean particiated;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDinnerParticipation() {
        return dinnerParticipation;
    }

    public void setDinnerParticipation(boolean dinnerParticipation) {
        this.dinnerParticipation = dinnerParticipation;
    }

    public boolean isParticiated() {
        return particiated;
    }

    public void setParticiated(boolean particiated) {
        this.particiated = particiated;
    }
}
