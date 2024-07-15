package lessons.lesson_04;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
@Table(name = "magic")
public class Magic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "название")
    private String name;
    @Column(name = "повреждение")
    private int damage;
    @Column(name = "атака")
    private int attBonus;
    @Column(name = "броня")
    private int armor;

    public Magic(String name, int damage, int attBonus, int armor) {
        this.name = name;
        this.damage = damage;
        this.attBonus = attBonus;
        this.armor = armor;
    }

    public Magic() {

    }

    @Override
    public String toString() {
        return "Magic{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", damage=" + damage +
               ", attBonus=" + attBonus +
               ", armor=" + armor +
               '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAttBonus(int attBonus) {
        this.attBonus = attBonus;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
