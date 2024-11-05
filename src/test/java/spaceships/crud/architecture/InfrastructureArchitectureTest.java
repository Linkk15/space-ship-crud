package spaceships.crud.architecture;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;

class InfrastructureArchitectureTest extends AbstractArchitectureTest {

  @Test
  void api_implementations_should_not_depend_on_application_classes() {
    noClasses()
        .that(areApiImplementations())
        .should()
        .dependOnClassesThat()
        .resideInAPackage(ANY_APPLICATION_PACKAGE)
        .because("the API should not depend on the application layers")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void api_implementations_should_reside_in_defined_package() {
    classes()
        .that(areApiImplementations())
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters.api.implementations..")
        .because("api implementations are adapters from the infrastructure")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void ports_should_reside_in_defined_package() {
    classes()
        .that(arePortImplementations())
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters.ports")
        .because("ports are adapters from the infrastructure")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void mappers_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Mapper")
        .should()
        .beInterfaces()
        .andShould()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE + "adapters.mappers..")
        .because("mappers should reside in the infrastructure layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  private DescribedPredicate<? super JavaClass> areApiImplementations() {
    return resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters.api.implementations");
  }

  private DescribedPredicate<? super JavaClass> arePortImplementations() {
    return resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters.ports");
  }
}
