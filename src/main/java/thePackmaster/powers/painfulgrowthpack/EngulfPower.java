package thePackmaster.powers.painfulgrowthpack;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import thePackmaster.powers.AbstractPackmasterPower;
import thePackmaster.util.Wiz;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class EngulfPower extends AbstractPackmasterPower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(EngulfPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int DEBUFF_REDUCTION = 1;
    public static final int GP_GAIN = 2;

    private int triggersLeftThisTurn;
    private AbstractPower cachedMidReductionPower = null;

    public EngulfPower(int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
        triggersLeftThisTurn = amount;
        updateDescription();
    }

    public EngulfPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        triggersLeftThisTurn = amount;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (stackAmount > 0) {
            triggersLeftThisTurn += stackAmount;
        }
        else {
            triggersLeftThisTurn = Math.min(triggersLeftThisTurn, this.amount);
        }

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        triggersLeftThisTurn = this.amount;
        updateDescription();
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power != cachedMidReductionPower) {
            return true;
        }

        cachedMidReductionPower = null;
        return (power.amount != 0);
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.type != PowerType.DEBUFF || triggersLeftThisTurn == 0 || owner.hasPower(ArtifactPower.POWER_ID)) {
            return stackAmount;
        }

        int reduction = Math.min(DEBUFF_REDUCTION, Math.abs(stackAmount));
        if (reduction == 0) { return stackAmount; }

        int retVal = stackAmount;

        this.flashWithoutSound();
        if (retVal > 0) {
            power.amount -= reduction;
            retVal -= reduction;
        } else {
            power.amount += reduction;
            retVal += reduction;
        }

        Wiz.applyToSelfTop(new GrowingPainPower(GP_GAIN));

        triggersLeftThisTurn -= 1;
        cachedMidReductionPower = power;

        this.updateDescription();
        return retVal;
    }

    @Override
    public void updateDescription() {
        this.description =
                DESCRIPTIONS[0] +
                        ((this.amount == 1) ? DESCRIPTIONS[1] : (this.amount + DESCRIPTIONS[2])) +
                        ((triggersLeftThisTurn < this.amount) ? (DESCRIPTIONS[3] + triggersLeftThisTurn + DESCRIPTIONS[4]) : "") +
                        DESCRIPTIONS[5];
    }
}
