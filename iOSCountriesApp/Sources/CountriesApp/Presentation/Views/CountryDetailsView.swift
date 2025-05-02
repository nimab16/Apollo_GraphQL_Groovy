import SwiftUI

struct CountryDetailsView: View {
    @StateObject private var viewModel: CountryDetailsViewModel
    
    init(viewModel: CountryDetailsViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    var body: some View {
        ZStack {
            if let country = viewModel.state.country {
                ScrollView {
                    VStack(alignment: .leading, spacing: 16) {
                        Text(country.name)
                            .font(.largeTitle)
                            .bold()
                        
                        Text(country.code)
                            .font(.title2)
                            .foregroundColor(.gray)
                        
                        VStack(alignment: .leading, spacing: 8) {
                            DetailRow(title: "Capital", value: country.capital)
                            DetailRow(title: "Currency", value: country.currency)
                            DetailRow(title: "Languages", value: country.languages.joined(separator: ", "))
                        }
                        .padding()
                        .background(Color(.systemBackground))
                        .cornerRadius(10)
                        .shadow(radius: 2)
                    }
                    .padding()
                }
            }
            
            if viewModel.state.isLoading {
                ProgressView()
            }
        }
        .navigationTitle("Country Details")
    }
}

struct DetailRow: View {
    let title: String
    let value: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.subheadline)
                .foregroundColor(.gray)
            Text(value)
                .font(.body)
        }
    }
} 