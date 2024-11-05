package spaceships.crud.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClass.Predicates;
import org.junit.jupiter.api.Test;

class DomainArchitectureTest extends AbstractArchitectureTest {

  @Test
  void domain_should_not_depend_on_application() {
    noClasses()
        .that()
        .resideInAPackage(ANY_DOMAIN_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(ANY_APPLICATION_PACKAGE)
        .because("the domain should be isolated from the application layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void domain_should_not_depend_on_infrastructure() {
    noClasses()
        .that()
        .resideInAPackage(ANY_DOMAIN_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(ANY_INFRASTRUCTURE_PACKAGE)
        .because("the domain should be isolated from the infrastructure")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void repositories_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Repository")
        .should()
        .beInterfaces()
        .andShould()
        .resideInAPackage(DOMAIN_PACKAGE + ".repositories..")
        .because("repositories should reside in the domain layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void domain_repositories_should_be_implemented_in_the_infrastructure() {
    classes()
        .that()
        .implement(domainRepositories())
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters.repositories..")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void domain_rest_apis_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Api")
        .should()
        .beInterfaces()
        .andShould()
        .resideInAPackage(DOMAIN_PACKAGE + ".api.interfaces..")
        .because("Apis interfaces should reside in the domain layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void domain_rest_apis_should_be_implemented_in_the_infrastructure() {
    classes()
        .that()
        .implement(domainRestInterfaces())
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE + ".adapters.api.implementations..")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void domain_enums_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Enum")
        .should()
        .beEnums()
        .andShould()
        .resideInAPackage(DOMAIN_PACKAGE + ".enums..")
        .because("Enums should reside in the domain layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void exceptions_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Exception")
        .should()
        .resideInAPackage(DOMAIN_PACKAGE + ".exceptions..")
        .because("Domain exceptions should reside in the domain layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  @Test
  void domain_entities_should_reside_in_defined_package() {
    classes()
        .that()
        .haveSimpleNameEndingWith("Entity")
        .should()
        .beEnums()
        .andShould()
        .resideInAPackage(DOMAIN_PACKAGE + ".entities..")
        .because("Entities should reside in the domain layer")
        .allowEmptyShould(true)
        .check(importedClasses);
  }

  private DescribedPredicate<? super JavaClass> domainRepositories() {
    return Predicates.resideInAPackage(DOMAIN_PACKAGE + ".repositories");
  }

  private DescribedPredicate<? super JavaClass> domainRestInterfaces() {
    return Predicates.resideInAPackage(DOMAIN_PACKAGE + ".api.interfaces");
  }
}
