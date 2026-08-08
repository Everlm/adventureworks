# Instrucciones generales

## Comunicación

- Responde siempre en español.
- Sé breve y directo.
- Responde primero a la pregunta y añade contexto solo cuando sea necesario.
- No uses expresiones como "¡Claro!", "¡Por supuesto!" o similares.
- No uses emojis.
- No expliques paso a paso salvo que se solicite explícitamente.
- No generes documentación, archivos README o ejemplos adicionales a menos que se soliciten.

## Código

- Prioriza código simple, legible y fácil de mantener.
- Sigue los principios SOLID cuando aporten valor, sin sobreingeniería.
- Aplica Clean Code: nombres descriptivos, funciones pequeñas y responsabilidades claras.
- Evita la duplicación de código (principio DRY).
- No añadas comentarios que describan lo evidente. El código debe ser autoexplicativo.
- Si detectas una solución más simple con el mismo resultado, prefiérela.
- Mantén consistencia con el estilo y la arquitectura existente del proyecto.

## Spring

- Sigue la arquitectura hexagonal del proyecto: `presentation → application → domain` e `infrastructure → domain`.
- Mantén `domain` libre de dependencias de Spring y JPA; las entidades y repositorios viven solo en `infrastructure/persistence`.
- Usa `@Service` para lógica de aplicación, `@Repository` para adaptadores de persistencia y `@RestController` + `@RequestMapping("/api/<recurso-plural>")` para controladores.
- Usa `@Component` para utilidades, validadores y clientes.
- Inyecta por constructor: prefiere `@RequiredArgsConstructor` con campos `final`; no uses `@Autowired`.
- Los servicios retornan siempre `Result<T>` con sus factory methods estáticos (`Success`, `NotFound`, `BadRequest`, etc.).
- Mantén controladores delgados: delegan al servicio y devuelven `ResponseEntity<Result<T>>` mediante `ResultExtensions.toResponseEntity(...)`.
- Separa DTOs de entrada (`{Entidad}Request`) y salida (`{Entidad}Response`) con Lombok; nunca expongas entidades ni modelos de dominio en los controladores.
- Usa MapStruct para mapear: `@Mapper(componentModel = "spring")` en `application/mapper` y el patrón `INSTANCE` en `infrastructure/persistence/mapper`.
- Valida con `AbstractValidator<T>` (java-fluent-validator) usando `ruleFor(...).must(...).withMessage(...).withFieldName(...).critical()`; evita Bean Validation salvo necesidad.
- Define puertos en `domain/repository` e implementaciones `{Entidad}RepositoryImpl` en `infrastructure/persistence/repository`.
- Usa proyecciones JPA (`{Entidad}Projection` + `@Query` JPQL) para consultas de solo lectura que combinan entidades.
- Aplica `@Transactional` solo en operaciones de escritura (`save`, `update`, `deleteById`).
- Usa `var`, streams con `.toList()`, `Optional` y switch expressions; constantes de enums en PascalCase (estilo del proyecto).
- Documenta los endpoints con `@Tag` y `@Operation` (descripciones en español).
- Escribe tests con JUnit 5 y stubs in-memory (sin Mockito); nómbralos `{método}{ResultadoEsperado}` (ej. `findByIdShouldReturnNotFoundWhenPersonIsMissing`).


## Antes de generar código

- Analiza primero el código existente.
- Reutiliza componentes, servicios y utilidades existentes antes de crear nuevos.
- No inventes APIs, métodos o propiedades que no existan en el proyecto.
- No cambies el comportamiento existente salvo que se solicite.
- Si falta información para responder con certeza, indícalo en lugar de asumir.
- Si existen varias soluciones válidas, elige la más sencilla y mantenible.
