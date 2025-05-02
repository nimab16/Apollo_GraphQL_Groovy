import Foundation
import Apollo

protocol GetCountryDetailUseCaseProtocol {
    func execute(code: String) async throws -> DetailCountry
}

final class GetCountryDetailUseCase: GetCountryDetailUseCaseProtocol {
    private let apollo: ApolloClient
    
    init(apollo: ApolloClient) {
        self.apollo = apollo
    }
    
    func execute(code: String) async throws -> DetailCountry {
        let query = GetCountryDetailQuery(code: code)
        let result = try await apollo.fetch(query: query)
        
        guard let country = result.data?.country else {
            throw NSError(domain: "Country not found", code: 404)
        }
        
        return DetailCountry(
            id: country.id,
            name: country.name,
            code: country.code,
            capital: country.capital,
            currency: country.currency,
            languages: country.languages.map { $0.name }
        )
    }
} 