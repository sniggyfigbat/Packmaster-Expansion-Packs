package thePackmaster.powers.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import thePackmaster.powers.AbstractPackmasterPower;
import thePackmaster.util.Wiz;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.*;

public class GrowingPainPower extends AbstractPackmasterPower {
    public static final String POWER_ID = makeID(GrowingPainPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int STACK_COUNT_TRIGGER = 10;
    public static final int TRIGGER_DAMAGE_PLAYER = 10;
    public static final int TRIGGER_DAMAGE_ENEMIES = 10;

    public GrowingPainPower(int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    public GrowingPainPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        if (card.freeToPlay() || card.cost < -1) {
            return;
        }

        int spentOnCard = 0;
        if (card.cost == -1) {
            spentOnCard = EnergyPanel.totalCount;
        }
        else {
            // This works because the function's called before the energy is actually spent.
            spentOnCard = Math.min(EnergyPanel.totalCount, card.costForTurn);
        }

        if (spentOnCard > 0) {
            GrowingPainPower t = this;
            final int s = spentOnCard;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    t.flashWithoutSound();
                    Wiz.applyToSelfTop(new GrowingPainPower(s));
                    updateDescription();

                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void onInitialApplication() {
        checkTrigger();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        checkTrigger();
    }

    private void checkTrigger() {
        if (this.amount >= STACK_COUNT_TRIGGER)
        {
            this.flash();

            // Add in inverse order, as we're pushing to the top of the queue
            att(new ApplyPowerAction(
                    this.owner,
                    this.owner,
                    new StrengthPower(this.owner, 1),
                    1
            )); // Fourth, add 1 Strength.
            att(new DamageAllEnemiesAction(
                    (AbstractPlayer) this.owner,
                    DamageInfo.createDamageMatrix(TRIGGER_DAMAGE_ENEMIES, true),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            )); // Third, damage all monsters (assuming they're not all dead already).
            att(new DamageAction(
                    this.owner,
                    new DamageInfo(this.owner, TRIGGER_DAMAGE_PLAYER, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            )); // Second, damage self.
            att(new RemoveSpecificPowerAction(
                    this.owner,
                    this.owner,
                    this
            )); // First, clear GP stack
        }
    }
}
