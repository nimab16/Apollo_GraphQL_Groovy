import Foundation

struct SimpleCountry: Identifiable, Equatable {
    let id: String
    let name: String
    let code: String
}

struct DetailCountry: Identifiable, Equatable {
    let id: String
    let name: String
    let code: String
    let capital: String
    let currency: String
    let languages: [String]
} 