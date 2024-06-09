package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.*;

public class ExplosiveBurst extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(ExplosiveBurst.class.getSimpleName());

    private static final int ATTACK_COUNT = 3;
    private static final int ATTACK_COUNT_UPG = 1;

    private static final int GP = 5;
    private static final int GP_UPG = 0;

    public ExplosiveBurst() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = GP;
        this.baseSecondMagic = ATTACK_COUNT;

        this.magicNumber = this.baseMagicNumber;
        this.secondMagic = this.baseSecondMagic;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(GP_UPG);
        upgradeSecondMagic(ATTACK_COUNT_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new GrowingPainPower(p, this.magicNumber), this.magicNumber));

        ExplosiveBurst t = this;
        int sm = this.secondMagic;
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPower gp = adp().getPower(GrowingPainPower.POWER_ID);
                if (gp != null && gp.amount > 0) {
                    t.baseDamage = gp.amount;
                    t.calculateCardDamage(m);

                    for (int i = 0; i < sm; i += 1) {
                        t.dmgTop(m, t.damage < 10 ? AttackEffect.SLASH_DIAGONAL : AttackEffect.SLASH_HEAVY);
                    }
                }

                this.isDone = true;
            }
        });
    }
}
