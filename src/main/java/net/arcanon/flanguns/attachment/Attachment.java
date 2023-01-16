package net.arcanon.flanguns.attachment;

import net.arcanon.flanguns.item.ItemType;

public abstract class Attachment {

    private String name;
    private ItemType type;

    private boolean silencer;
    private double damageMultiplier;
    private double spreadMultiplier;
    private double recoilMultiplier;

    public Attachment(String name, boolean silencer, double damageMultiplier, double spreadMultiplier, double recoilMultiplier) {
        this.name = name;
        this.type = ItemType.ATTACHMENT;
        this.silencer = silencer;
        this.damageMultiplier = damageMultiplier;
        this.spreadMultiplier = spreadMultiplier;
        this.recoilMultiplier = recoilMultiplier;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public boolean isSilencer() {
        return silencer;
    }

    public void setSilencer(boolean silencer) {
        this.silencer = silencer;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public double getSpreadMultiplier() {
        return spreadMultiplier;
    }

    public void setSpreadMultiplier(double spreadMultiplier) {
        this.spreadMultiplier = spreadMultiplier;
    }

    public double getRecoilMultiplier() {
        return recoilMultiplier;
    }

    public void setRecoilMultiplier(double recoilMultiplier) {
        this.recoilMultiplier = recoilMultiplier;
    }
}