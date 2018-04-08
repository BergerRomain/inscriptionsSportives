package inscriptions;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class ImprovedMySQLDialect extends MySQL5InnoDBDialect {
    @Override
    public String getDropSequenceString(String sequenceName) {
        return "drop sequence if exists " + sequenceName;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }
}
