package cenibee.github.rememote.common;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * Base assembler class for convert Domain type to Model type.
 * <p>
 * this abstract class is implementation of {@link RepresentationModel}.
 * @param <T> Origin Domain Type.
 * @param <D> Model Type to Convert
 */
public abstract class BaseModelAssembler<T, D extends RepresentationModel<D>>
        implements RepresentationModelAssembler<T, D> {

    @NonNull
    public D toModel(@NonNull T note) {
        return assemble(note)
                .add(links(note));
    }

    abstract protected D assemble(T entity);
    abstract protected Collection<Link> links(T entity);
}
