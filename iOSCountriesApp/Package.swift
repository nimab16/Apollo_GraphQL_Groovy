// swift-tools-version:5.5
import PackageDescription

let package = Package(
    name: "CountriesApp",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .library(
            name: "CountriesApp",
            targets: ["CountriesApp"]),
    ],
    dependencies: [
        .package(url: "https://github.com/apollographql/apollo-ios.git", from: "1.0.0")
    ],
    targets: [
        .target(
            name: "CountriesApp",
            dependencies: [
                .product(name: "Apollo", package: "apollo-ios")
            ]),
        .testTarget(
            name: "CountriesAppTests",
            dependencies: ["CountriesApp"]),
    ]
) 