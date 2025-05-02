import Foundation
import Combine

@MainActor
final class CountriesListViewModel: ObservableObject {
    @Published private(set) var state: CountriesListState = .init()
    
    private let getCountriesUseCase: GetCountriesUseCaseProtocol
    
    init(getCountriesUseCase: GetCountriesUseCaseProtocol) {
        self.getCountriesUseCase = getCountriesUseCase
        loadCountries()
    }
    
    private func loadCountries() {
        Task {
            state.isLoading = true
            do {
                let countries = try await getCountriesUseCase.execute()
                state.countries = countries
            } catch {
                // Handle error
                print("Error loading countries: \(error)")
            }
            state.isLoading = false
        }
    }
}

struct CountriesListState {
    var countries: [SimpleCountry] = []
    var isLoading: Bool = false
} 