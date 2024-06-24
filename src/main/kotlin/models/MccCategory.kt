package io.github.klahap.mccgen.models


enum class MccCategory(val label: String, val range: IntRange) {
    AgriculturalServices("Agricultural services", 1..1499),
    ContractedServices("Contracted services", 1500..2999),
    TransportationServices("Transportation services", 4000..4799),
    UtilityServices("Utility services", 4800..4999),
    RetailOutletServices("Retail outlet services", 5000..5599),
    ClothingShops("Clothing shops", 5600..5699),
    MiscellaneousShops("Miscellaneous shops", 5700..7299),
    BusinessServices("Business services", 7300..7999),
    ProfessionalServicesAndMembershipOrganisations("Professional services and membership organisations", 8000..8999),
    GovernmentServices("Government services", 9000..9999),
}
