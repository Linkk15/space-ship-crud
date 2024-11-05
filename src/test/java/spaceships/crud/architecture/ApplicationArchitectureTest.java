package spaceships.crud.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;

class ApplicationArchitectureTest extends AbstractArchitectureTest {

  @Test
  void application_should_not_depend_on_infrastructure() {
    noClasses()
        .that()
        .resideInAPackage(ANY_APPLICATION_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(ANY_INFRASTRUCTURE_PACKAGE)
        .because("the application layer should be isolated from the infrastructure")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void use_case_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("UseCase")
        .should()
        .beInterfaces()
        .andShould()
        .resideInAPackage(APPLICATION_PACKAGE + ".usecases..");
  }

  @Test
  void ports_should_be_implemented_in_the_infrastructure() {
    classes()
        .that()
        .implement(infrastructure_ports())
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters..")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  private DescribedPredicate<? super JavaClass> infrastructure_ports() {
    return JavaClass.Predicates.resideInAPackage(APPLICATION_PACKAGE + ".ports");
  }
}
