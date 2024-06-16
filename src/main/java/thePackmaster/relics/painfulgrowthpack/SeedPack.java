package thePackmaster.relics.painfulgrowthpack;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.orbs.painfulgrowthpack.Seed;
import thePackmaster.packs.PainfulGrowthPack;
import thePackmaster.relics.AbstractPackmasterRelic;

public class SeedPack extends AbstractPackmasterRelic {
    public static final String RELIC_ID = SpireAnniversary5Mod.makeID(SeedPack.class.getSimpleName());

    public SeedPack() {
        super(RELIC_ID, RelicTier.COMMON, LandingSound.MAGICAL, PainfulGrowthPack.ID);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.channelOrb(new Seed());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SeedPack();
    }
}
