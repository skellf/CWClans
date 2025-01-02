package me.skellf.cwclans.clans;

import java.util.Arrays;
import java.util.List;

public class Clan {
    private int id;
    private String name;
    private String leader;
    private List<String> members;
    private int coins;

    public Clan(int id, String name, String leader, String members, int coins) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.members = Arrays.asList(members.split(","));
        this.coins = coins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
