package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.dimensiongatepack2.MagicMissile;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.atb;

public class Germinate extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(Germinate.class.getSimpleName());

    private static final int BLOCK = 4;
    private static final int BLOCK_UPG = 2;

    private static final int BAB_UPG_COUNT = 0;
    private static final int BAB_UPG_COUNT_UPG = 1;

    public Germinate(){
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = BAB_UPG_COUNT;

        this.block = this.baseBlock;
        this.magicNumber = this.baseMagicNumber;

        this.cardsToPreview = new BoomAndBust();
    }

    @Override
    public void upp() {
        upgradeBlock(BLOCK_UPG);
        upgradeMagicNumber(BAB_UPG_COUNT_UPG);

        this.cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, p, this.block));
        atb(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block)));

        AbstractCard bab = new BoomAndBust();
        for (int i = 0; i < this.magicNumber; i++) {
            bab.upgrade();
        }
        atb(new MakeTempCardInDrawPileAction(bab, 1, true, true));
    }
}
