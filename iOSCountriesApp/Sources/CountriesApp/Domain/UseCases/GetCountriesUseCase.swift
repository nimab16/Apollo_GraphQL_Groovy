import Foundation
import Apollo

protocol GetCountriesUseCaseProtocol {
    func execute() async throws -> [SimpleCountry]
}

final class GetCountriesUseCase: GetCountriesUseCaseProtocol {
    private let apollo: ApolloClient
    
    init(apollo: ApolloClient) {
        self.apollo = apollo
    }
    
    func execute() async throws -> [SimpleCountry] {
        let query = GetCountriesQuery()
        let result = try await apollo.fetch(query: query)
        
        return result.data?.countries.map { country in
            SimpleCountry(
                id: country.id,
                name: country.name,
                code: country.code
            )
        } ?? []
    }
} 