package me.skellf.cwclans.clans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Clan {
    private final int id;
    private final String name;
    private final String leader;
    private final List<String> members;
    private final int coins;
    private final List<String> lore;
    private final int rating;

    public Clan(int id, String name, String leader, String members, int coins, String lore, int rating) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.members = Arrays.asList(members.split(","));
        this.coins = coins;
        this.lore = lore.isEmpty() ? new ArrayList<>() : Arrays.asList(lore.split(","));
        this.rating = rating;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getLeader() {
        return leader;
    }


    public List<String> getMembers() {
        return members;
    }

    public int getCoins() {
        return coins;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getRating() {
        return rating;
    }
}
