package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.adts.MyIStack;
import models.adts.MyStack;
import models.exceptions.MyException;

public class CompStmt implements IStmt{
    private final IStmt first, snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), snd.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + first.toString() + " ; " + snd.toString() + ")";
    }

}
