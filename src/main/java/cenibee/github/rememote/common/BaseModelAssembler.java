package cenibee.github.rememote.common;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Base assembler class for convert Domain type to Model type.
 * <p>
 * this abstract class is implementation of {@link RepresentationModel}.
 * @param <T> Origin Domain Type.
 * @param <D> Model Type to Convert
 */
public interface BaseModelAssembler<T, D extends RepresentationModel<D>>
        extends RepresentationModelAssembler<T, D> {

    @NonNull
    @Override
    default D toModel(@NonNull T note) {
        return assemble(note)
                .add(links(note));
    }

    @NonNull
    @Override
    default CollectionModel<D> toCollectionModel(Iterable<? extends T> entities) {

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                        .add(links(entities));
    }

    D assemble(T entity);
    default Collection<Link> links(T entity) {
        return List.of();
    }
    default Collection<Link> links(Iterable<? extends T> entities) {
        return List.of();
    }
}
