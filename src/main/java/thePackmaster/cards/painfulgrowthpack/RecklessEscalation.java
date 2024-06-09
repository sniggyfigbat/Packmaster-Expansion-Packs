package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.RecklessEscalationPower;

import static thePackmaster.util.Wiz.atb;

public class RecklessEscalation extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(RecklessEscalation.class.getSimpleName());

    public RecklessEscalation(){
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.isInnate = false;
    }

    @Override
    public void upp() {
        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new RecklessEscalationPower(p, 1), 1));
    }
}
