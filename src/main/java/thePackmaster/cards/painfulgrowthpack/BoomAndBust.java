package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.adp;
import static thePackmaster.util.Wiz.atb;

public class BoomAndBust extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(BoomAndBust.class.getSimpleName());

    private static final int CARD_DRAW_COUNT = 1;
    private static final int CARD_DRAW_COUNT_UPG = 1;

    private static final int GP = 5;

    public BoomAndBust(){
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = CARD_DRAW_COUNT;

        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(CARD_DRAW_COUNT_UPG);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToTop(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            atb(new ApplyPowerAction(
                    adp(),
                    adp(),
                    new GrowingPainPower(adp(), GP), GP
            ));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
}
