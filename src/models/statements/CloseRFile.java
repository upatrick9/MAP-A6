package models.statements;

import models.PrgState;
import models.adts.MyIFileTable;
import models.exceptions.MyException;
import models.expressions.Exp;
import models.types.StringType;
import models.values.StringValue;
import models.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private final Exp exp;

    public CloseRFile(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value value = exp.eval(state.getSymTable(), state.getHeap());
        if (!(value.getType() instanceof StringType))
            throw new MyException("closeRFile: expression does not evaluate to a string!");

        StringValue fileName = (StringValue) value;

        if (!fileTable.isDefined(fileName))
            throw new MyException("closeRFile: file not opened: " + fileName.getVal());

        BufferedReader br = fileTable.lookup(fileName);

        try {
            br.close();

            fileTable.remove(fileName);

        } catch (IOException e) {
            throw new MyException("closeRFile: " + e.getMessage());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
