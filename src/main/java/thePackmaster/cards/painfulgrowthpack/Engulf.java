package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.EngulfPower;

import static thePackmaster.util.Wiz.atb;

public class Engulf extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(Engulf.class.getSimpleName());

    private static final int COST = 2;
    private static final int COST_UPG = -1;

    public Engulf(){
        super(ID, COST, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeBaseCost(COST + COST_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new EngulfPower(p, 1), 1));
    }
}
