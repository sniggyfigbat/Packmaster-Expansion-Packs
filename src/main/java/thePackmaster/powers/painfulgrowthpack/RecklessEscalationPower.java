package thePackmaster.powers.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thePackmaster.powers.AbstractPackmasterPower;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

public class RecklessEscalationPower extends AbstractPackmasterPower {
    public static final String POWER_ID = makeID(RecklessEscalationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int TEMP_STRENGTH_GAIN = 1;
    public static final int GP_GAIN = 1;

    public RecklessEscalationPower(int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
        updateDescription();
    }

    public RecklessEscalationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK) {
            super.onAfterUseCard(card, action);
            return;
        }

        int tempStrength = (this.amount * TEMP_STRENGTH_GAIN);
        int gp = (this.amount * GP_GAIN);

        this.flashWithoutSound();
        atb(new ApplyPowerAction(this.owner, this.owner, new GrowingPainPower(this.owner, gp), gp));
        atb(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, tempStrength), tempStrength));
        atb(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, tempStrength), tempStrength));
        super.onAfterUseCard(card, action);
    }

    @Override
    public void updateDescription() {
        this.description =
                DESCRIPTIONS[0] +
                        (this.amount * TEMP_STRENGTH_GAIN) +
                        DESCRIPTIONS[1] +
                        (this.amount * GP_GAIN) +
                        DESCRIPTIONS[2];
    }
}
