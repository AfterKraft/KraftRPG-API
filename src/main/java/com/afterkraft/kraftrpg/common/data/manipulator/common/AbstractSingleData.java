package com.afterkraft.kraftrpg.common.data.manipulator.common;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

/**
 * An abstraction for the various {@link DataManipulator}s that handle a single
 * value, adding the provided {@link #getValue()} and {@link #setValue(Object)}
 * methods for easy manipulation. This as well simplifies handling various
 * other common implementations, such as {@link #hashCode()} and
 * {@link #equals(Object)}.
 *
 * @param <E> The type of single value this will hold
 * @param <T_Manipulator> The type of {@link DataManipulator}
 * @param <T_Immutable> The type of {@link ImmutableDataManipulator}
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSingleData<E, T_Manipulator extends DataManipulator<T_Manipulator, T_Immutable>,
        T_Immutable extends ImmutableDataManipulator<T_Immutable, T_Manipulator>>
        extends AbstractData<T_Manipulator, T_Immutable> {

    protected final Key<? extends BaseValue<E>> usedKey;
    private E value;

    protected AbstractSingleData(Class<T_Manipulator> manipulatorClass, E value, Key<? extends BaseValue<E>> usedKey) {
        super(manipulatorClass);
        this.value = checkNotNull(value);
        this.usedKey = checkNotNull(usedKey);
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(this.usedKey, AbstractSingleData.this::getValue);
        registerFieldSetter(this.usedKey, this::setValue);
        registerKeyValue(this.usedKey, AbstractSingleData.this::getValueGetter);
    }

    protected abstract Value<?> getValueGetter();

    @Override
    public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
        // we can delegate this since we have a direct value check as this is
        // a Single value.
        return key == this.usedKey ? Optional.of((E) this.value) : super.get(key);
    }

    @Override
    public boolean supports(Key<?> key) {
        return checkNotNull(key) == this.usedKey;
    }

    // We have to have this abstract to properly override for generics.
    @Override
    public abstract T_Immutable asImmutable();

    // Again, overriding for generics
    @Override
    public abstract int compareTo(T_Manipulator o);

    public E getValue() {
        return this.value;
    }

    public T_Manipulator setValue(E value) {
        this.value = checkNotNull(value);
        // double casting due to jdk 6 type inference
        return (T_Manipulator) this;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hashCode(this.value);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final AbstractSingleData other = (AbstractSingleData) obj;
        return Objects.equal(this.value, other.value);
    }
}
