# mccgen

Welcome to `mccgen`, a Gradle plugin designed to generate Merchant Category Codes (MCC) for your Kotlin projects. This plugin retrieves MCC codes from the Stripe documentation and generates corresponding Kotlin code, simplifying the integration of these codes into your project.

## Features

- Fetches MCC codes from Stripe documentation.
- Generates Kotlin code for easy usage in your projects.
- Customizable package name and output directory.

## Getting Started

### Installation

Add the `mccgen` plugin to your `build.gradle.kts` file:

```kotlin
plugins {
    id("io.github.klahap.mccgen") version "1.0.0"
}
```

Configure the plugin in your `build.gradle.kts` file:

```kotlin
mccCodeGenerator {
    source = MccSource.STRIPE
    packageName = "io.my.package.mcc"
    output = "$buildDir/generated/mcc"
}
```

### Usage

Run the following Gradle task to generate MCC codes:

```sh
./gradlew generateMccCodes
```

This will generate the MCC codes and place them in the specified output directory.

```kotlin
enum class MccCategory(val label: String) {
  AgriculturalServices("Agricultural services"),
  ContractedServices("Contracted services"),
  TransportationServices("Transportation services"),
  UtilityServices("Utility services"),
  RetailOutletServices("Retail outlet services"),
  ClothingShops("Clothing shops"),
  MiscellaneousShops("Miscellaneous shops"),
  BusinessServices("Business services"),
  ProfessionalServicesAndMembershipOrganisations("Professional services and membership organisations"),
  GovernmentServices("Government services"),
}

enum class Mcc(
    val value: String,
    val label: String,
    val code: Int,
    val category: MccCategory,
) {
    AcRefrigerationRepair(
        value = "ac_refrigeration_repair",
        label = "A/C, Refrigeration Repair",
        code = 7623,
        category = MccCategory.BusinessServices,
    ),
    AccountingBookkeepingServices(
        value = "accounting_bookkeeping_services",
        label = "Accounting/Bookkeeping Services",
        code = 8931,
        category = MccCategory.ProfessionalServicesAndMembershipOrganisations
    ),
    // ...
}
```

## Contributing

We welcome contributions to the `mccgen` project. If you have any ideas, suggestions, or issues, please feel free to open an issue or a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
