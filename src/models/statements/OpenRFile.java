package models.statements;

import models.PrgState;
import models.adts.MyIFileTable;
import models.exceptions.MyException;
import models.expressions.Exp;
import models.types.StringType;
import models.values.StringValue;
import models.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    private final Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value value = exp.eval(state.getSymTable(), state.getHeap());
        if (!(value.getType() instanceof StringType))
            throw new MyException("openRFile: expression does not evaluate to a string!");

        StringValue fileName = (StringValue) value;

        if (fileTable.isDefined(fileName))
            throw new MyException("openRFile: file already opened: " + fileName.getVal());

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.getVal()));

            fileTable.put(fileName, br);

        } catch (IOException e) {
            throw new MyException("openRFile: " + e.getMessage());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}
