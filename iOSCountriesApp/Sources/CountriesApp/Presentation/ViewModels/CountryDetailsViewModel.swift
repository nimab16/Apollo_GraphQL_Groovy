import Foundation
import Combine

@MainActor
final class CountryDetailsViewModel: ObservableObject {
    @Published private(set) var state: CountryDetailsState = .init()
    
    private let getCountryDetailUseCase: GetCountryDetailUseCaseProtocol
    private let countryCode: String
    
    init(
        countryCode: String,
        getCountryDetailUseCase: GetCountryDetailUseCaseProtocol
    ) {
        self.countryCode = countryCode
        self.getCountryDetailUseCase = getCountryDetailUseCase
        loadCountryDetails()
    }
    
    private func loadCountryDetails() {
        Task {
            state.isLoading = true
            do {
                let country = try await getCountryDetailUseCase.execute(code: countryCode)
                state.country = country
            } catch {
                // Handle error
                print("Error loading country details: \(error)")
            }
            state.isLoading = false
        }
    }
}

struct CountryDetailsState {
    var country: DetailCountry?
    var isLoading: Bool = false
} 