package models.statements;

import models.PrgState;
import models.adts.MyIHeap;
import models.adts.MyIDictionary;
import models.adts.MyIStack;
import models.exceptions.MyException;
import models.expressions.Exp;
import models.types.BoolType;
import models.values.BoolValue;
import models.values.Value;

public class WhileStmt implements IStmt {
    private final Exp expression;
    private final IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        MyIStack<IStmt> stack = prgState.getExeStack();
        MyIDictionary<String, Value> symTable = prgState.getSymTable();
        MyIHeap<Value> heap = prgState.getHeap();

        Value cond = expression.eval(symTable, heap);
        if (!cond.getType().equals(new BoolType())) {
            throw new MyException("While condition is not boolean: " + cond);
        }

        BoolValue boolCond = (BoolValue) cond;
        if (boolCond.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "While(" + expression.toString() + ") " + statement.toString();
    }
}
