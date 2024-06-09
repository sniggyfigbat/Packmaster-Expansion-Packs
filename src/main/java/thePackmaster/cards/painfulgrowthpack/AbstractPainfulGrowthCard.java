package thePackmaster.cards.painfulgrowthpack;

import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.cards.AbstractPackmasterCard;

import java.util.Locale;

public abstract class AbstractPainfulGrowthCard extends AbstractPackmasterCard {

    public AbstractPainfulGrowthCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);

        if (!SpireAnniversary5Mod.oneFrameMode) {
            setBackgroundTexture(
                    "anniv5Resources/images/512/artificer/" + type.name().toLowerCase(Locale.ROOT) + ".png",
                    "anniv5Resources/images/1024/artificer/" + type.name().toLowerCase(Locale.ROOT) + ".png"
            );
        }
    }

    public AbstractPainfulGrowthCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this (cardID, cost, type, rarity, target, ThePackmaster.Enums.PACKMASTER_RAINBOW);
    }
}
