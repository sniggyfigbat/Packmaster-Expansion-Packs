package thePackmaster.powers.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thePackmaster.cards.painfulgrowthpack.NewGrowth;
import thePackmaster.powers.AbstractPackmasterPower;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

public class EndlessFlexibilityPower extends AbstractPackmasterPower {
    public static final String POWER_ID = makeID(EndlessFlexibilityPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EndlessFlexibilityPower(int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
        updateDescription();
    }

    public EndlessFlexibilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.flashWithoutSound();
        for (int i = 0; i < this.amount; i++) {
            AbstractCard ng = new NewGrowth();
            atb(new MakeTempCardInHandAction(ng));
        }
    }

    @Override
    public void updateDescription() {
        this.description =
                this.amount <= 1 ?
                        DESCRIPTIONS[0] :
                        DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}
