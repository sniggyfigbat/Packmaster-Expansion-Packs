package thePackmaster.packs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.painfulgrowthpack.*;
import thePackmaster.orbs.painfulgrowthpack.Seed;

import java.util.ArrayList;

public class PainfulGrowthPack extends AbstractCardPack {

    public static final String ID = SpireAnniversary5Mod.makeID("PainfulGrowthPack");
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    public static final String NAME = UI_STRINGS.TEXT[0];
    public static final String DESC = UI_STRINGS.TEXT[1];
    public static final String AUTHOR = UI_STRINGS.TEXT[2];

    public PainfulGrowthPack() {
        super(ID, NAME, DESC, AUTHOR, new PackSummary(3, 2, 4, 4, 3, PackSummary.Tags.Discard));
    }

    public static void onPowersModified() {
        Seed.onPowersModified();
    }

    @Override
    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(SurgingDefend.ID);            // hand add anniv5:SurgingDefend 1 0
        cards.add(Gardening.ID);                // hand add anniv5:Gardening 1 0
        cards.add(Germinate.ID);                // hand add anniv5:Germinate 1 0
        cards.add(DispersalStrike.ID);          // hand add anniv5:DispersalStrike 1 0
        cards.add(MarginalGain.ID);             // hand add anniv5:MarginalGain 1 0
        cards.add(Engulf.ID);                   // hand add anniv5:Engulf 1 0
        cards.add(RampantGrowth.ID);            // hand add anniv5:RampantGrowth 1 0
        cards.add(ExplosiveBurst.ID);           // hand add anniv5:ExplosiveBurst 1 0
        cards.add(RecklessEscalation.ID);       // hand add anniv5:RecklessEscalation 1 0
        cards.add(EndlessFlexibility.ID);       // hand add anniv5:EndlessFlexibility 1 0
        return cards;
    }
}