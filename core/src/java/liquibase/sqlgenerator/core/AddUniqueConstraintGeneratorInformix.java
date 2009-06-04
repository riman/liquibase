package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.InformixDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.statement.AddUniqueConstraintStatement;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class AddUniqueConstraintGeneratorInformix extends AddUniqueConstraintGenerator {

	public AddUniqueConstraintGeneratorInformix() {

    }

    @Override
    public int getPriority() {
        return SqlGenerator.PRIORITY_DATABASE;
    }

    @Override
	public boolean supports(AddUniqueConstraintStatement statement, Database database) {
        return (database instanceof InformixDatabase);
	}

	@Override
	public Sql[] generateSql(AddUniqueConstraintStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

		final String sqlNoContraintNameTemplate = "ALTER TABLE %s ADD CONSTRAINT UNIQUE (%s)";
		final String sqlTemplate = "ALTER TABLE %s ADD CONSTRAINT UNIQUE (%s) CONSTRAint %s";
		if (statement.getConstraintName() == null) {
			return new Sql[] {
				new UnparsedSql(String.format(sqlNoContraintNameTemplate 
						, database.escapeTableName(statement.getSchemaName(), statement.getTableName())
						, database.escapeColumnNameList(statement.getColumnNames())
				))
			};
		} else {
			return new Sql[] {
				new UnparsedSql(String.format(sqlTemplate
						, database.escapeTableName(statement.getSchemaName(), statement.getTableName())
						, database.escapeColumnNameList(statement.getColumnNames())
						, database.escapeConstraintName(statement.getConstraintName())
				))
			};
		}


		
	}


}
