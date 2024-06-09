package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.actions.gemspack.ReduceDebuffsAction;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.atb;

public class MarginalGain extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(MarginalGain.class.getSimpleName());

    private static final int BLOCK = 4;

    private static final int DEBUFF_REDUCTION = 1;

    private static final int GP = 1;
    private static final int GP_UPG = 1;

    public MarginalGain(){
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = GP;

        this.block = this.baseBlock;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(GP_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, p, this.block));
        atb(new ReduceDebuffsAction(p, DEBUFF_REDUCTION));
        atb(new ApplyPowerAction(p, p, new GrowingPainPower(p, this.magicNumber), this.magicNumber));
    }
}
