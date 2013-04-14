package com.mike724.networkapi;

/**
 * User: Dakota
 * Date: 4/14/13
 * Time: 1:32 AM
 */
public class NetworkPlayer {
    private String player;
    private Integer tokens;
    private Boolean isBanned;
    private Boolean isOnline;
    private NetworkRank rank;

    public NetworkPlayer(String player, Integer tokens, Boolean isBanned, Boolean isOnline, NetworkRank rank) {
        this.player = player;
        this.tokens = tokens;
        this.isBanned = isBanned;
        this.isOnline = isOnline;
        this.rank = rank;
    }

    public Integer getTokens() {
        return tokens;
    }

    public String getPlayer() {
        return player;
    }

    public Boolean isBanned() {
        return isBanned;
    }

    public Boolean isOnline() {
        return isOnline;
    }

    public NetworkRank getRank() {
        return rank;
    }

    public void setOnline(Boolean b) {
        isOnline = b;
    }

    public void setRank(NetworkRank nr) {
        rank = nr;
    }

    public void setTokens(Integer i) {
        tokens = i;
    }

    public void setPlayer(String s) {
        player = s;
    }

    public void setBanned(Boolean b) {
        isBanned = b;
    }
}
