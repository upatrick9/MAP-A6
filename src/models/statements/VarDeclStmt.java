package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.exceptions.MyException;
import models.exceptions.VariableAlreadyExists;
import models.types.BoolType;
import models.types.IntType;
import models.types.StringType;
import models.types.Type;
import models.values.BoolValue;
import models.values.IntValue;
import models.values.StringValue;
import models.values.Value;

public class VarDeclStmt implements IStmt {
    private final String name;
    private final Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name))
            throw new VariableAlreadyExists();

        symTable.update(name, type.defaultValue());

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(this.name, this.type);
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
