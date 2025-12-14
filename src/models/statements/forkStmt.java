package models.statements;

import models.adts.*;
import models.PrgState;
import models.exceptions.*;
import models.values.Value;

public class forkStmt implements IStmt {
    private final IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        state.getSymTable().getContent().forEach((k, v) -> newSymTable.update(k, v.deepCopy()));

        return new PrgState(
                new MyStack<>(),
                newSymTable,
                state.getOut(),
                state.getFileTable(),
                state.getHeap(),
                stmt
        );

    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }
}
