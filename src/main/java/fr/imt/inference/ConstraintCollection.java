package fr.imt.inference;

import fr.imt.logger.Logger;
import fr.imt.inference.type.Type;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ConstraintCollection is used in both phases of the algorithm, is it a good idea ?
 */
public class ConstraintCollection implements Substituable<ConstraintCollection> {

    private final Logger logger = new Logger();

    private Deque<Constraint> constraints = new LinkedList<>();

    public ConstraintCollection() {
        this.constraints = new LinkedList<>();
    }

    public ConstraintCollection(Deque<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void add(Type t1, Type t2) {
        logger.debug("Adding constraint : (" + t1 + ", " + t2 + ")");
        constraints.add(new Constraint(t1, t2));
    }

    public int size() {
        return constraints.size();
    }

    public boolean isEmpty() {
        return constraints.isEmpty();
    }

    public Constraint head() {
        return constraints.getFirst();
    }

    public ConstraintCollection tail() {
        Deque<Constraint> newConstraints = new LinkedList<>();
        newConstraints.addAll(this.constraints);
        newConstraints.poll(); // remove the first element
        return new ConstraintCollection(newConstraints); // todo -> turn not to be a singleton anymore
    }

    @Override
    public ConstraintCollection applySubstitution(SubstitutionCollection substitutions) {
        List<Constraint> newConstaints = this.constraints.stream()
                .map(constraint -> constraint.applySubstitution(substitutions))
                .collect(Collectors.toList());

        return new ConstraintCollection(new LinkedList<>(newConstaints));
    }

    @Override
    public String toString() {
        return constraints.toString();
    }
}
