package me.skellf.cwclans.clans.menu;

import me.skellf.cwclans.CWClans;
import me.skellf.cwclans.clans.Clan;
import me.skellf.cwclans.utils.ClanManager;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.annotation.Position;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Arrays;
import java.util.List;

public class GeneralMenu extends MenuPagged<Clan> {

    private final ClanManager cm;
    private final MessageConfig mc;
    private int currentSort = 0;

    @Position(4)
    private final Button sortButton;

    @Position(49)
    private final Button createButton;

    public GeneralMenu() {
        super(54, CWClans.getInstance().getClanManager().getAllClans());
        this.cm = CWClans.getInstance().getClanManager();
        this.mc = CWClans.getInstance().getMessageConfig();

        this.setTitle("• Кланы » Страница: " + this.getCurrentPage());

        this.sortButton = new Button() {

            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                currentSort = (currentSort + 1) % 4;
                redrawButtons();
            }

            // TODO: Сделать отслеживание времени в DBManager
            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.PAPER,
                        "&#5ce31eСортировка",
                        "",
                                "&f- " + (currentSort == 0 ? "&6" : "&f") + "По дате создания",
                                "&f- " + (currentSort == 1 ? "&6" : "&f") + "По рейтингу",
                                "&f- " + (currentSort == 2 ? "&6" : "&f") + "По количеству коинов",
                                "&f- " + (currentSort == 3 ? "&6" : "&f") + "По количеству участников",
                                "",
                                "&f ЛКМ, для изменения")
                        .make();
            }

        };

        this.createButton = new Button() {

            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                player.closeInventory();
                player.sendMessage(mc.getMessage("clan.create.usage"));
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.WRITABLE_BOOK,
                        "&#5ce31eСоздать клан",
                        "",
                        "&f ЛКМ, чтобы создать клан")
                        .make();
            }

        };
    }

    @Override
    protected ItemStack convertToItemStack(Clan clan) {
        ItemCreator clanItem = ItemCreator.of(CompMaterial.BOOK,
                clan.getName(),
                "",
                "&f Лидер: " + clan.getLeader(),
                "&f Участников: &6" + clan.getMembers().size(),
                "",
                "&f Рейтинг: &6" + clan.getRating(),
                "&f Коинов: &6" + clan.getCoins());

        if (!clan.getLore().isEmpty()) {
            clanItem.lore("", "&f Описание:");
            for (String loreLine : clan.getLore()) {
                clanItem.lore("&f" + loreLine);
            }
        }

        return clanItem.make();
    }

    @Override
    protected void onPageClick(Player player, Clan clan, ClickType clickType) {

    }

    @Override
    public List<Integer> getSlots() {
        return Arrays.asList(10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43);
    }

    @Override
    protected boolean canShowPreviousButton(){
        return getCurrentPage() > 1;
    }

    @Override
    protected boolean canShowNextButton() {
        return getCurrentPage() < getPages().size();
    }

    @Override
    protected int getPreviousButtonPosition() {
        return this.getSize() - 9 + 2;
    }

    @Override
    protected int getNextButtonPosition() {
        return this.getSize() - 9 + 6;
    }

    @Override
    public Button formPreviousButton(){
        if (getCurrentPage() <= 1) {
            return Button.makeEmpty();
        }

        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                setCurrentPage(Math.max(getCurrentPage() - 1, 1));
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.PAPER,
                                "&aСледущая страница")
                        .modelData(10005)
                        .make();
            }
        };
    }

    @Override
    public Button formNextButton() {
        if (getCurrentPage() >= getPages().size()) {
            return Button.makeEmpty();
        }

        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                setCurrentPage(Math.min(getCurrentPage() + 1, getPages().size()));
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.PAPER,
                                "&cПредыдущая страница")
                        .modelData(10006)
                        .make();
            }
        };
    }
}
