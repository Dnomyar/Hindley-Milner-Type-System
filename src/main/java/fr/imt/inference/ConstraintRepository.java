package fr.imt.inference;

import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//@Singleton
public class ConstraintRepository {

    private final Logger logger = new Logger();

    private Deque<Constraint> constraints = new LinkedList<>();

    public ConstraintRepository() {
        this.constraints = new LinkedList<>();
    }

    public ConstraintRepository(Deque<Constraint> constraints) {
        this.constraints = constraints;
    public List<Tuple<Type, Type>> getConstraints(){
        return this.constraints;
    }

    public void uni(Type t1, Type t2) {
        logger.debug("Adding constraint : (" + t1 + ", " + t2 + ")");
        constraints.add(new Constraint(t1, t2));
    }

    public boolean isEmpty() {
        return constraints.isEmpty();
    }

    public Constraint head() {
        return constraints.getFirst();
    }

    public ConstraintRepository tail() {
        Deque<Constraint> newConstraints = new LinkedList<>();
        newConstraints.addAll(this.constraints);
        newConstraints.poll(); // remove the first element
        return new ConstraintRepository(newConstraints); // todo -> turn not to be a singleton anymore
    }

    @Override
    public ConstraintRepository applySubstitution(SubstitutionCollection substitutions) {
        List<Constraint> newConstaints = this.constraints.stream()
                .map(constraint -> constraint.applySubstitution(substitutions))
                .collect(Collectors.toList());

        return new ConstraintRepository(new LinkedList<>(newConstaints));
    }

    @Override
    public String toString() {
        return constraints.toString();
    }
}
