package thePackmaster.relics.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.packs.PainfulGrowthPack;
import thePackmaster.relics.AbstractPackmasterRelic;

public class Fertiliser extends AbstractPackmasterRelic {
    public static final String RELIC_ID = SpireAnniversary5Mod.makeID(Fertiliser.class.getSimpleName());

    public Fertiliser() {
        super(RELIC_ID, RelicTier.COMMON, LandingSound.FLAT, PainfulGrowthPack.ID);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount >= 10 && damageAmount < 15) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return 15;
        } else {
            return damageAmount;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Fertiliser();
    }
}
